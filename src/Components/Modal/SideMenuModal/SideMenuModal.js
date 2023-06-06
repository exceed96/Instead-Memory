import React from "react";
import styles from "./SideMenuModal.module.css";
import { useDispatch } from "react-redux";
import { headerActions } from "../../../store/headerState";
const SideMenuModal = (props) => {
  const dispatch = useDispatch();

  const modalEventHandler = () => {
    dispatch(headerActions.closeModal());
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
