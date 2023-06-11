const loginLogout = document.getElementById('loginLogout');
const token = localStorage.getItem('token');

function base64UrlDecode(base64Url) {
  let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  while (base64.length % 4) {
    base64 += '=';
  }
  return atob(base64);
}

function parseJwt(token) {
  const parts = token.split('.');
  if (parts.length !== 3) {
    throw new Error('Invalid JWT token');
  }

  const headerJson = base64UrlDecode(parts[0]);
  const payloadJson = base64UrlDecode(parts[1]);

  const header = JSON.parse(headerJson);
  const payload = JSON.parse(payloadJson);

  return { header, payload };
}

if (token) {
  const logoutButton = document.createElement('a');
  logoutButton.textContent = 'Logout';
  logoutButton.classList.add('clickable');

  loginLogout.innerHTML = '';
  loginLogout.appendChild(logoutButton);

  logoutButton.addEventListener('click', () => {
    localStorage.removeItem('token');
    fetch("http://localhost:8080/logout")
    window.location.href = 'login.html';
  });
}

if (token != null) {
  const { header, payload } = parseJwt(token);
  if (payload.authorities !== 'ROLE_ADMIN' && document.getElementById("admin-button")) {
    document.getElementById("admin-button").remove();
  }
}
