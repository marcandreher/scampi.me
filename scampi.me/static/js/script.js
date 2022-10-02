document.addEventListener('DOMContentLoaded', () => {
    const $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);
    if ($navbarBurgers.length > 0) {
      $navbarBurgers.forEach( el => {
        el.addEventListener('click', () => {
          const target = el.dataset.target;
          const $target = document.getElementById(target);
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
  function onCopy(copied) {
    navigator.clipboard.writeText(copied);
    alert("Copied the link: "+copied);
  }

  function onSubmit() {
    var text = document.getElementById("linkTxt");
    if(isValidHttpUrl(text.value) == false) {
        alert("This url isn't valid!");
        return false;
    }else{
      
      $.ajax({
        url: '/create',
        type: 'POST',
        data: {url: text.value},
        success: function (result) {
            var div = document.createElement("div");
            const veri = document.getElementById('veri');
            div.classList.toggle("link-box")
            div.classList.toggle("box")
            div.classList.toggle("has-text-centered")
            var dm = result.split("\"");
            text.value = "";
            veri.style.color = "red";
            div.innerHTML = "<i class=\"fa-solid fa-paperclip\"></i> <a href=\"%url%\">%url%</a> <i class=\"fa-solid fa-arrows-turn-right\"></i> <a href=\"%new%\">%new%    <a onclick=\"onCopy('%new%')\"class=\"button is-small is-copy\">Copy</a>"
            .replaceAll("%new%", domain + "/" + dm[0]).replaceAll("%url%", dm[1]);
          document.getElementById("messages").appendChild(div);
        }
      });

    }
  }