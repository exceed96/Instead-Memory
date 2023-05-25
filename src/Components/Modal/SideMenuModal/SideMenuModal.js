import React from "react";
import styles from "./SideMenuModal.module.css";

const SideMenuModal = (props) => {
  const modalEventHandler = () => {
    props.setModal(false);
  };

  return (
    <React.Fragment>
      <section className={styles.modalContent}>
        <section className={styles.sideBarHeader}>
          <strong className={styles.userName}>{props.user}</strong>
        </section>
      </section>
      <section
        className={styles.modalOverlay}
        onClick={modalEventHandler}
      ></section>
    </React.Fragment>
  );
};

export default SideMenuModal;
