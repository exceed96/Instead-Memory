const memo = document.querySelectorAll(".memo");
const savedMemoRight = document.querySelectorAll(".savedMemoRight");

memo.forEach((el) => {
    el.addEventListener("mouseover", () => {
        el.children[0].children[0].children[1].style.opacity = "1";
    })
    el.addEventListener("mouseout", () => {
        el.children[0].children[0].children[1].style.opacity = "0";
    })
})