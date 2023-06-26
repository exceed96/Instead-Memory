import jwt_decode from "jwt-decode";
import { Reset } from "styled-reset";
import Main from "../components/Main/Main";
import Header from "../components/Header/Header";

function MemoHomePage() {
  if (!localStorage.getItem("userToken")) {
    const searchParams = new URLSearchParams(window.location.search);
    localStorage.setItem("userToken", searchParams.get("AccessToken"));
  }
  const user = jwt_decode(localStorage.getItem("userToken"));
  return (
    <>
      <Reset />
      <Header user={user.username}></Header>
      <Main />
      {/* <Footer /> */}
    </>
  );
}

export default MemoHomePage;
