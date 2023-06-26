import { useCallback, useEffect, useState } from "react";
import LoginComponent from "../components/Main/LoginComponent/LoginComponent";

function LoginPage() {
  useEffect(() => {
    localStorage.clear();
  }, []);

  return (
    <>
      <LoginComponent />
    </>
  );
}

export default LoginPage;
