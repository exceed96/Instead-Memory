import React from "react";
import { Reset } from "styled-reset";
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
