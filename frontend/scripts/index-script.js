
const uploadButton = document.getElementById('upload-button');
        
uploadButton.addEventListener('click', function() {
    window.location.href = 'upload.html';
});


// Function to fetch and display the uploaded pictures
function displayUploadedPictures() {
  fetch('http://localhost:8080/api/pictures', {
    method: 'GET'
  })
    .then(response => {
      if (response.status === 200) {
        return response.json();
      } else {
        throw new Error('Failed to fetch pictures.');
      }
    })
    .then(data => {
      const uploadedPicturesContainer = document.getElementById('uploaded-pictures');
      uploadedPicturesContainer.innerHTML = '';
    
      data.pictures.forEach(picture => {
        const pictureItem = document.createElement('div');
        pictureItem.classList.add('picture-item');

        const pictureImage = document.createElement('img');
        pictureImage.src = picture.picture;
        pictureImage.alt = picture.description;

        const pictureDescription = document.createElement('p');
        pictureDescription.textContent = picture.description;

        pictureItem.appendChild(pictureImage);
        pictureItem.appendChild(pictureDescription);
        uploadedPicturesContainer.appendChild(pictureItem);
      });
    })
    .catch(error => {
      console.error('Error:', error);
    });
}
// Call the function to display uploaded pictures when the page loads
window.addEventListener('load', displayUploadedPictures);

if(token == null){
  document.getElementById("upload-button").remove();
} 