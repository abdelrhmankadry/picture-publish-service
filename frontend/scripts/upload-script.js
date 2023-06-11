const uploadForm = document.getElementById('upload-form');
const descriptionInput = document.getElementById('description');
const categoryInput = document.getElementById('category');
const pictureInput = document.getElementById('picture');


function handleUploadFormSubmit(event) {
  event.preventDefault();

  const description = descriptionInput.value;
  const category = categoryInput.value;
  const picture = pictureInput.files[0];

  const reader = new FileReader();
  reader.onloadend = () => {
    const base64Picture = reader.result.split(',')[1];
    const token = localStorage.getItem('token');

    fetch('http://localhost:8080/api/picture', {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        description,
        category: category.toUpperCase(),
        picture: base64Picture
      })
    })
    .then(response => {
      if (response.ok) {
        alert('Picture uploaded successfully!');
      } else {
        alert('Error uploading picture. Please try again.');
      }
    })
    .catch(error => {
      console.error('Error:', error);
    });
  };
  reader.readAsDataURL(picture);
  window.location.href = 'index.html';
}

uploadForm.addEventListener('submit', handleUploadFormSubmit);
