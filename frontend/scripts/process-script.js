const pictureDetailsContainer = document.getElementById('picture-details-container');
const acceptButton = document.getElementById('accept-button');
const rejectButton = document.getElementById('reject-button');
const urlParams = new URLSearchParams(window.location.search);

// Function to create and display the picture details on the page
function createPictureDetails() {
  const picture = urlParams.get('picture');
  const description = urlParams.get('description');
  const category = urlParams.get('category').replace(/_/g, ' ').toLowerCase();
  const pictureDetails = document.createElement('div');
  const pictureImage = document.createElement('img');

  // Fetch the image and set the pictureImage source to the blob URL
  fetch(picture, {
      method: 'GET',
      headers: {
          'Authorization': `Bearer ${token}` 
      }
  })
  .then(response => {
      if (response.ok) {
          return response.blob();
      } else {
          throw new Error('Request failed');
      }
  })
  .then(imageBlob => {
      pictureImage.src = URL.createObjectURL(imageBlob)
  })
  .catch(error => {
      console.log(error); 
  });

  pictureImage.alt = description;

  // After the image loads, display the dimensions
  pictureImage.onload = () => {
      const dimensions = document.createElement('h3');
      dimensions.textContent = `Dimensions: ${pictureImage.width} x ${pictureImage.height}`;
      pictureDetails.appendChild(dimensions);
  }

  const pictureDescription = document.createElement('h3');
  pictureDescription.textContent = `Description: ${description}`;

  const pictureCategory = document.createElement('h3');
  pictureCategory.textContent = `Category: ${category}`;

  // Append the elements to the pictureDetails container
  pictureDetails.appendChild(pictureImage);
  pictureDetails.appendChild(pictureDescription);
  pictureDetails.appendChild(pictureCategory);
  pictureDetailsContainer.appendChild(pictureDetails);
}

// Function to process the picture and make a PATCH request to the server
function processPicture(decision, id) {
  fetch(`http://localhost:8080/api/${decision}-picture/${id}`, {
      method: 'PATCH',
      headers: {
          'Authorization': `Bearer ${token}` 
      }
  })
}

// Add event listeners to the accept and reject buttons
acceptButton.addEventListener('click', () => {
  processPicture('accept', urlParams.get('id'));
  window.location.href = 'admin.html';
});

rejectButton.addEventListener('click', () => {
  processPicture('reject', urlParams.get('id'));
  window.location.href = 'admin.html';
});

// On page load, create and display the picture details
window.addEventListener('load', createPictureDetails);
