let registerBtn = document.getElementById("registerBtn");
registerBtn.onclick = async function () {
  const url = "http://127.0.0.1:1010/api/v1/account/register/register.html";
  const obj = {
    method: "GET",
    headers: {
      Accept: "application/json",
    },
  };
  const response = await fetch(url);
  console.log(typeof response);
  alert(typeof response);
  doSomething()
};

function doSomething() {
  // alert("post")
  const xhr = new XMLHttpRequest();
  const url = "http://127.0.0.1:1010/api/v1/account/register";
  xhr.open("POST", url, true);
//  xhr.setRequestHeader("Accept", "application/json");
  xhr.setRequestHeader("Content-Type", "application/json");
  let obj = JSON.stringify({
    id: 4567,
    name: "dfghj",
  });

  xhr.send(obj);
}
