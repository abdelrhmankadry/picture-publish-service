(function() {
    const form = document.getElementById('login-form');
  
    function handleFormSubmit(event) {
      event.preventDefault();
      const username = document.getElementById('username').value;
      const password = document.getElementById('password').value;
      sendLoginRequest(username, password);
    }
  
  
    function sendLoginRequest(username, password) {
      fetch('http://localhost:8080/api/signin', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify({
              email: username,
              password: password
          })
      })
      .then(response => {
          if (response.status === 200) {
              return response.json();
          } else {
              throw new Error('Login failed. Invalid username or password.');
          }
      })
      .then(data => {
          localStorage.setItem('token', data.accessToken);
          window.location.href = 'index.html';
      })
      .catch(error => {
          console.log('Error:', error);
          alert(error.message);
      });
    }
  
    form.addEventListener('submit', handleFormSubmit);
  })();
  