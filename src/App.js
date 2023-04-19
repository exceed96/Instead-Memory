import React from "react";
import { Reset } from "styled-reset";
import Header from "./Components/Header";
import Main from "./Components/Main";
import Footer from "./Components/Footer";
import jwt_decode from "jwt-decode";

function App() {
  const searchParams = new URLSearchParams(window.location.search);
  localStorage.setItem("userToken", searchParams.get("token"));
  const user = jwt_decode(searchParams.get("token"));
  console.log(user.username);
  return (
    <React.Fragment>
      <Reset />
      <Header user={user.username}></Header>
      <Main />
      <Footer />
    </React.Fragment>
  );
}

export default App;
