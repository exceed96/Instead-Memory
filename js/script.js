const memo = document.querySelectorAll(".memo");
const savedMemoRight = document.querySelectorAll(".savedMemoRight");

const sidebarOverlay = document.querySelector(".modalOverlay");
const sidebarOpenBtn = document.querySelector(".togglebtn");
const sidebar = document.querySelector(".modalContent");

const importMemoOpen = document.querySelector(".main-starMemoToggle");
const importMemoSection = document.querySelector(".importantMemoModalContent");
const importMemoOverlay = document.querySelector(".importantMemoModalOverlay");
const clickToTop = document.getElementById('footerRight-clickToTop');

const moveclickToTop=()=>{
    if(window.scrollY > 0 ){
        window.scrollTo({top:0, behavior:"smooth"});
    }
}

memo.forEach((el) => {
  el.addEventListener("mouseover", () => {
    el.children[0].children[0].children[1].style.opacity = "1";
  });
  el.addEventListener("mouseout", () => {
    el.children[0].children[0].children[1].style.opacity = "0";
  });
});

sidebarOpenBtn.addEventListener("click", () => {
  sidebarOverlay.classList.remove("show");
  sidebar.classList.remove("show");
});

sidebarOverlay.addEventListener("click", () => {
  sidebarOverlay.classList.add("show");
  sidebar.classList.add("show");
});

importMemoOpen.addEventListener("click", () => {
  importMemoOverlay.classList.remove("show");
  importMemoSection.classList.remove("show");
});

importMemoOverlay.addEventListener("click", () => {
  importMemoOverlay.classList.add("show");
  importMemoSection.classList.add("show");
});

clickToTop.addEventListener('click',moveclickToTop);