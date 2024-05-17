import { Outlet } from "react-router-dom";
import jwt_decode from "jwt-decode";
import { Reset } from "styled-reset";
import Main from "../components/Main/Main";
import Header from "../components/Header/Header";
import Footer from "../components/Footer/Footer";

function MemoHomePage() {
  if (!localStorage.getItem("userToken")) {
    const searchParams = new URLSearchParams(window.location.search);
    localStorage.setItem("userToken", searchParams.get("AccessToken"));
    let currentUrl = window.location.href;
    let newUrl = currentUrl.replace(/\/\?AccessToken=.*/, "");
    window.history.replaceState({}, document.title, newUrl);
  }

  const user = jwt_decode(localStorage.getItem("userToken"));
  return (
    <>
      <Reset />
      <Header user={user.username}></Header>
      <Main />
      <Footer />
      <Outlet />
    </>
  );
}

export default MemoHomePage;
