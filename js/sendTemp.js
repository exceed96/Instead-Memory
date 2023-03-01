const form = document.querySelector(".main-memo");

form.addEventListener("submit", async (evt) => {
  evt.preventDefault();
  const sendData = {
    title: form.elements.title.value,
    content: form.elements.content.value,
  };
  const data = await axios
    .post("http://3.34.168.144:8080/v1/memo", sendData)
    .then((response) => console.log("success"))
    .catch((err) => console.log(err));
  form.elements.title.value = "";
  form.elements.content.value = "";
});
