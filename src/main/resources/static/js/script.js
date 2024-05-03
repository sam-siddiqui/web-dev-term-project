const pianoKeys = document.querySelectorAll(".piano-keys .key"),
volumeSlider = document.querySelector(".volume-slider input"),
keysCheckbox = document.querySelector(".keys-checkbox input");

let allKeys = [],
audio = new Audio(`tunes/a.wav`); // by default, audio src is "a" tune

const playTune = (key) => {
    audio.src = `tunes/${key}.wav`; // passing audio src based on key pressed 
    audio.play(); // playing audio

    const clickedKey = document.querySelector(`[data-key="${key}"]`); // getting clicked key element
    clickedKey.classList.add("active"); // adding active class to the clicked key element
    setTimeout(() => { // removing active class after 150 ms from the clicked key element
        clickedKey.classList.remove("active");
    }, 150);
}

pianoKeys.forEach(key => {
    allKeys.push(key.dataset.key); // adding data-key value to the allKeys array
    // calling playTune function with passing data-key value as an argument
    key.addEventListener("click", () => playTune(key.dataset.key));
});

const handleVolume = (e) => {
    audio.volume = e.target.value; // passing the range slider value as an audio volume
}

const showHideKeys = () => {
    // toggling hide class from each key on the checkbox click
    pianoKeys.forEach(key => key.classList.toggle("hide"));
}

const pressedKey = (e) => {
    // if the pressed key is in the allKeys array, only call the playTune function
    if(allKeys.includes(e.key)) playTune(e.key);
}

keysCheckbox.addEventListener("click", showHideKeys);
volumeSlider.addEventListener("input", handleVolume);
document.addEventListener("keydown", pressedKey);



    function search() {
        // Get the input value
        var keyword = document.getElementById("keyword").value;

        // Make an AJAX request to searchstudent endpoint passing the keyword as a parameter
        // Example using Fetch API
        fetch('/searchstudent?keyword=' + keyword)
            .then(response => {
                if (response.ok) {
                    return response.text();
                }
                throw new Error('Network response was not ok.');
            })
            .then(data => {
                // Handle the search result data as needed
                console.log(data);
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    }
    function searchSchedule() {
        // Get the input value
        var keyword = document.getElementById("keyword").value;

        // Make an AJAX request to searchstudent endpoint passing the keyword as a parameter
        // Example using Fetch API
        fetch('/searchstudent?keyword=' + keyword)
            .then(response => {
                if (response.ok) {
                    return response.text();
                }
                throw new Error('Network response was not ok.');
            })
            .then(data => {
                // Handle the search result data as needed
                console.log(data);
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    }
    function clearSearch() {
    document.getElementById("searchForm").reset(); // Reset the form to its default values
    // Alternatively, you can clear the input field manually:
    // document.getElementById("searchInput").value = "";
}
document.getElementById("keyword").addEventListener("input", function(event) {
    var keyword = event.target.value.trim(); // Get the search keyword
    if (keyword.length > 0) {
        // Perform AJAX request to search endpoint
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/searchstudent?keyword=" + encodeURIComponent(keyword), true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                // Update search results div with response
                document.getElementById("searchResults").innerHTML = xhr.responseText;
            }
        };
        xhr.send();
    } else {
        // Clear search results if the keyword is empty
        document.getElementById("searchResults").innerHTML = "";
    }
});
function clearSearch() {
    document.getElementById("searchForm").reset(); // Reset the form to its default values
    // Alternatively, you can clear the input field manually:
    // document.getElementById("searchInput").value = "";
}

    


