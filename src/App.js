import "./App.css";
import { Reset } from "styled-reset";
import MemoHomePage from "./pages/MemoHome";
import LoginPage from "./pages/Login";
import ErrorPage from "./pages/Error";
import { RouterProvider, createBrowserRouter } from "react-router-dom";

const router = createBrowserRouter([
  {
    path: "/",
    element: <LoginPage />,
  },
  {
    path: "/memo",
    element: <MemoHomePage />,
  },
  {
    path: "/error",
    element: <ErrorPage />,
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
