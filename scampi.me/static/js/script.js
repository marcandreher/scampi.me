document.addEventListener('DOMContentLoaded', () => {

    // Get all "navbar-burger" elements
    const $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);
  
    // Check if there are any navbar burgers
    if ($navbarBurgers.length > 0) {
  
      // Add a click event on each of them
      $navbarBurgers.forEach( el => {
        el.addEventListener('click', () => {
  
          // Get the target from the "data-target" attribute
          const target = el.dataset.target;
          const $target = document.getElementById(target);
  
          // Toggle the "is-active" class on both the "navbar-burger" and the "navbar-menu"
          el.classList.toggle('is-active');
          $target.classList.toggle('is-active');
  
        });
      });
    }
  
  });

  function onInputChanged() {
        var text = document.getElementById("linkTxt").value;
        const veri = document.getElementById('veri');
        if(isValidHttpUrl(text) == true) {
            veri.style.color = "green";
        }else{
            veri.style.color = "red";
        }

  }

  function isValidHttpUrl(string) {
    let url;
    
    try {
      url = new URL(string);
    } catch (_) {
      return false;  
    }
  
    return true;
  }

  function onSubmit() {
    var text = document.getElementById("linkTxt").value;
    if(isValidHttpUrl(text) == false) {
        alert("This url isn't valid!");
        return false;
    }else{
        var form = document.getElementById("form");
        form.submit();
        return true;
    }
  }