import React from "react";
import styles from "./ImportMemoToggle.module.css";
import ImportantModalContent from "../Modal/ImportantModalContent/ImportantModalContent";
import { useSelector, useDispatch } from "react-redux";
import { importMemoActions } from "../../store/importMemoState";

const ImportMemoToggle = (props) => {
  const modal = useSelector((state) => state.importMemoState.modal);
  const isOpen = useSelector((state) => state.importMemoState.isOpen);
  const dispatch = useDispatch();

  const modalEventHandler = () => {
    dispatch(importMemoActions.onOffModal());
  };

  if (isOpen) {
    document.body.style.overflow = "hidden";
  } else {
    document.body.style.overflow = "auto";
  }

  return (
    <React.Fragment>
      {modal && <ImportantModalContent modalOffHandler={modalEventHandler} />}
      <div
        className={styles["main-starMemoToggle"]}
        onClick={modalEventHandler}
      >
        <i className="fas fa-chevron-down"></i>
      </div>
    </React.Fragment>
  );
};

export default ImportMemoToggle;
