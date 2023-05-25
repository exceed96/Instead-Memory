import { Reset } from "styled-reset";

const LoginComponent = () => {
  return (
    <>
      <Reset />
      <a href="http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/auth/google">
        Google
      </a>
      <a href="http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/auth/naver">
        Naver
      </a>
    </>
  );
};

export default LoginComponent;
