import { useEffect } from "react";
import { Reset } from "styled-reset";
import styles from "./LoginComponent.module.css";
import naverLogin from "../../../assets/naverlogin.png";
import googleLogin from "../../../assets/googlelogin.png";

const LoginComponent = () => {
  const naverLoginHandler = () => {
    window.location.href =
      "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/auth/naver";
  };
  const googleLoginHandler = () => {
    window.location.href =
      "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/auth/google";
  };

  useEffect(() => {
    localStorage.clear();
  }, []);

  return (
    <section className={styles.loginContainer}>
      <Reset />
      <div className={styles.google} onClick={googleLoginHandler}>
        <img src={googleLogin} alt="icon-googleLogin" />
      </div>
      <div className={styles.naver} onClick={naverLoginHandler}>
        <img src={naverLogin} alt="icon-naverLogin" />
      </div>
    </section>
  );
};

export default LoginComponent;
