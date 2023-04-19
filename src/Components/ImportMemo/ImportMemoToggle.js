import React, { useState } from "react";
import styles from "./ImportMemoToggle.module.css";
import ImportantModalContent from "../Modal/ImportantModalContent.js/ImportantModalContent";

const ImportMemoToggle = (props) => {
  const [modal, setModal] = useState(false);
  const [isOpen, setIsOpen] = useState(false);

  const modalEventHandler = () => {
    setModal((prevState) => !prevState);
    setIsOpen((isOpen) => !isOpen);
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
