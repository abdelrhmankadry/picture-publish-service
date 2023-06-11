function displayPendingPictures() {
    // Get token from local storage
    const token = localStorage.getItem('token'); 
  
    // Fetch pending pictures
    fetch('http://localhost:8080/api/pending-pictures', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => {
        if (response.status === 200) {
            return response.json();
        } else {
            throw new Error('Failed to fetch pending pictures.');
        }
    })
    .then(data => {
        // Clear the container before rendering pictures
        const pendingPicturesContainer = document.getElementById('pending-pictures');
        pendingPicturesContainer.innerHTML = '';
  
        // Render each picture
        data.pictures.forEach(picture => {
            // Create picture element
            const pictureElement = document.createElement('div');
            pictureElement.classList.add('picture-item');
  
            // Create picture img element
            const pictureImg = document.createElement('img');
            fetch(picture.picture, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}` 
                }
            }).then(response => {
                if (response.ok) {
                    return response.blob();
                } else {
                    throw new Error('Request failed');
                }
            })
            .then(imageBlob => {
                // Set the img src attribute
                pictureImg.src = URL.createObjectURL(imageBlob);
            })
            .catch(error => {
                console.error(error); 
            });
            pictureImg.alt = picture.description;
  
            // Create picture description element
            const pictureDescription = document.createElement('p');
            pictureDescription.textContent = picture.description;
  
            // Add click listener to open the picture processing page
            pictureImg.addEventListener('click', () => {
                const url = new URL('process.html', window.location.href);
                url.searchParams.append('picture', picture.picture);
                url.searchParams.append('description', picture.description);
                url.searchParams.append('category', picture.category);
                url.searchParams.append('id', picture.uuid);
                window.location.href = url.href;
            });
  
            // Append picture elements to the pending pictures container
            pictureElement.appendChild(pictureImg);
            pictureElement.appendChild(pictureDescription);
            pendingPicturesContainer.appendChild(pictureElement);
        });
    })
    .catch(error => {
        console.error(error);
    });
  }
  
  window.addEventListener('load', displayPendingPictures);
  