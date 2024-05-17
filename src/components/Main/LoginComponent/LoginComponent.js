import { useEffect } from "react";
import { Reset } from "styled-reset";
import styles from "./LoginComponent.module.css";
import naverLoginImg from "../../../assets/naverlogin.png";
import googleLoginImg from "../../../assets/googlelogin.png";
import { Link } from "react-router-dom";

const LoginComponent = () => {
  // const naverLoginHandler = () => {
  //   window.location.href =
  //     "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/auth/naver";
  // };
  // const googleLoginHandler = () => {
  //   window.location.href =
  //     "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/auth/google";
  // };

  const naverLogin =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080";
  const googleLogin =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080";
  useEffect(() => {
    localStorage.clear();
  }, []);

  return (
    <section className={styles.loginContainer}>
      <Reset />
      <Link to={`${naverLogin}/auth/google`} className={styles.google}>
        <img src={googleLoginImg} alt="icon-googleLogin" />
      </Link>
      <Link to={`${googleLogin}/auth/naver`} className={styles.naver}>
        <img src={naverLoginImg} alt="icon-naverLogin" />
      </Link>
    </section>
  );
};

export default LoginComponent;
