import React from "react";
import styles from "./ImportantModalContent.module.css";
import ReactDOM, { createPortal } from "react-dom";

const Overlay = (props) => {
  return (
    <section
      className={styles.importantMemoModalOverlay}
      onClick={props.modalOffHandler}
    ></section>
  );
};

const ModalContent = (props) => {
  return (
    <section className={styles.importantMemoModalContent}>
      <section className={styles.importantMemoSection}></section>
    </section>
  );
};

const ImportantModalContent = (props) => {
  return (
    <React.Fragment>
      {ReactDOM.createPortal(
        <Overlay modalOffHandler={props.modalOffHandler} />,
        document.getElementById("overlay")
      )}
      {ReactDOM.createPortal(
        <ModalContent />,
        document.getElementById("modalContent")
      )}
    </React.Fragment>
  );
};

export default ImportantModalContent;
