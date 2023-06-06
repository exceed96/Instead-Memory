import { useEffect } from "react";
import jwt_decode from "jwt-decode";
import axios from "axios";
import { Reset } from "styled-reset";
import Header from "../Components/Header";
import Main from "../Components/Main";
import Footer from "../Components/Footer";
import { useCookies } from "react-cookie";

function MemoHomePage() {
  const [cookie, setCookie] = useCookies(["refresh"]);
  const searchParams = new URLSearchParams(window.location.search);
  localStorage.setItem("userToken", searchParams.get("AccessToken"));
  const user = jwt_decode(searchParams.get("AccessToken"));
  const getUrl =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo/find";

  const header = {
    "Content-Type": "application/json",
    Authorization: `Bearer ${localStorage.getItem("userToken")}`,
  };

  const getReTokenUrl =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/auth/login";

  const getRefresh = async () => {
    const response = await fetch(getReTokenUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      credentials: "include",
      body: JSON.stringify({ email: user.userNum }),
    })
      .then((response) => {
        console.log(document.cookie);
      })
      .catch(() => {});
  };

  useEffect(async () => {
    await getRefresh();
    const data = await axios({
      method: "get",
      url: getUrl,
      headers: header,
    });
    const cookie = document.cookie;
    console.log(cookie);
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
