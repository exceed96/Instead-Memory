import React, { useEffect } from "react";
import { Reset } from "styled-reset";
import Header from "./Components/Header";
import Main from "./Components/Main";
import Footer from "./Components/Footer";
import jwt_decode from "jwt-decode";
import axios from "axios";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import MemoHomePage from "./pages/MemoHome";
import LoginPage from "./pages/Login";

const router = createBrowserRouter([
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/memo",
    element: <MemoHomePage />,
  },
]);

function App() {
  return (
    <>
      <RouterProvider router={router}>
        <Reset />
      </RouterProvider>
      {/* <Reset />
      <Header user={user.username}></Header>
      <Main />
      <Footer /> */}
    </>
  );
}

export default App;
