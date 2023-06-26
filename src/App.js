import "./App.css";
import { Reset } from "styled-reset";
import MemoHomePage from "./pages/MemoHome";
import LoginPage from "./pages/Login";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

const router = createBrowserRouter([
  {
    path: "/",
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
    </>
  );
}

export default App;
