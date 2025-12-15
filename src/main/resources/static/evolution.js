// Utility to get query param from URL
function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

// Mock: Auth/JWT token - replace this in real usage
const JWT_TOKEN = localStorage.getItem('jwt_token') || '';

// Assume ID comes from /evolution.html?appointmentId=123
const appointmentId = getQueryParam('appointmentId');

// Elements
const appointmentInfo = document.getElementById('appointment-info');
const form = document.getElementById('evolution-form');
const formMessage = document.getElementById('form-message');

// 1. Load appointment info (patient, etc) - adjust endpoint as needed
if (appointmentId) {
    fetch(`/appointments/${appointmentId}`, {
        headers: { 'Authorization': `Bearer ${JWT_TOKEN}` }
    })
        .then(res => res.ok ? res.json() : Promise.reject(res))
        .then(data => {
            appointmentInfo.innerHTML = `<strong>Appointment ID:</strong> ${data.idAppointment || '-'}<br>
                <strong>Patient ID:</strong> ${data.patientId || '-'}<br>
                <strong>Date/Time:</strong> ${data.appointmentDateTime || '-'}<br>
                <strong>Start:</strong> ${data.startAt || '-'}<br>
                <strong>Status:</strong> ${data.status || '-'}`;
        })
        .catch(() => {
            appointmentInfo.innerHTML = '<span style="color:#c62828">Could not load appointment info.</span>';
        });
} else {
    appointmentInfo.innerHTML = '<span style="color:#c62828">No appointment selected.</span>';
}

// 2. Handle form submission
form.addEventListener('submit', function (e) {
    e.preventDefault();
    formMessage.textContent = '';
    formMessage.classList.remove('error');

    // Basic validation
    const bloodPressure = form.bloodPressure.value.trim();
    const heartRate = parseInt(form.heartRate.value, 10);
    const diagnosis = form.diagnosis.value.trim();
    if (!bloodPressure || !heartRate || !diagnosis) {
        formMessage.textContent = 'Please fill in all required fields.';
        formMessage.classList.add('error');
        return;
    }
    
    // Optional fields
    const temperature = form.temperature.value.trim() ? parseFloat(form.temperature.value) : null;
    const oxygenSaturation = form.oxygenSaturation.value.trim() ? parseInt(form.oxygenSaturation.value, 10) : null;
    
    // Prepare body
    const requestBody = {
        bloodPressure,
        heartRate,
        diagnosis,
        observations: form.observations.value.trim()
    };
    
    // Add optional fields only if they have values
    if (temperature !== null) {
        requestBody.temperature = temperature;
    }
    if (oxygenSaturation !== null) {
        requestBody.oxygenSaturation = oxygenSaturation;
    }

    fetch(`/appointments/${appointmentId}/evolution`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${JWT_TOKEN}`
        },
        body: JSON.stringify(requestBody)
    })
    .then(res => res.ok ? res.json() : res.json().then(data => Promise.reject(data)))
    .then(resp => {
        formMessage.textContent = 'Clinical evolution registered successfully!';
        form.reset();
        formMessage.classList.remove('error');
        setTimeout(() => {
            window.location.href = '/doctor/my-route.html'; // or show a success message/page
        }, 1200);
    })
    .catch(err => {
        formMessage.textContent = (err && err.message) || 'Could not register evolution. Please check your data.';
        formMessage.classList.add('error');
    });
});
