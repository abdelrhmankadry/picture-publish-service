const form = document.getElementById('register-form');

form.addEventListener('submit', function(event) {
    event.preventDefault();

    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');

    const registerData = {
        email: emailInput.value,
        password: passwordInput.value
    };

    fetch('http://localhost:8080/api/signup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(registerData)
    })
    .then(response => response.json())
    .then(data => {
        if(data.code == "EMAIL_EXISTS"){
            window.alert("Email already exists");
        } else {
            window.location.href = 'login.html';
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
});
