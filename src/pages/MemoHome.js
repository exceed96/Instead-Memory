import { useEffect } from "react";
import jwt_decode from "jwt-decode";
import axios from "axios";
import { Reset } from "styled-reset";
import Header from "../Components/Header";
import Main from "../Components/Main";
import Footer from "../Components/Footer";

function MemoHomePage() {
  const searchParams = new URLSearchParams(window.location.search);
  localStorage.setItem("userToken", searchParams.get("AccessToken"));
  localStorage.setItem("userRefreshToken", searchParams.get("RefreshToken"));
  const user = jwt_decode(searchParams.get("AccessToken"));
  const getUrl =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo/find";
  const header = {
    Authorization: `Bearer ${localStorage.getItem("userToken")}`,
  };
  useEffect(async () => {
    const data = await axios({
      method: "get",
      url: getUrl,
      headers: header,
    });
    console.log(data);
  }, []);
  return (
    <>
      <Reset />
      <Header user={user.username}></Header>
      <Main />
      <Footer />
    </>
  );
}

export default MemoHomePage;
