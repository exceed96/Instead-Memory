import React, { useEffect } from "react";
import styles from "./Header.module.css";
import icon from "../assets/icon.png";
import SideMenuModal from "./Modal/SideMenuModal/SideMenuModal";
import { useSelector, useDispatch } from "react-redux";
import { headerActions } from "../store/headerState";

const Header = (props) => {
  const dispatch = useDispatch();
  const modal = useSelector((state) => state.headerState.modal);
  const isOpen = useSelector((state) => state.headerState.isOpen);
  const width = useSelector((state) => state.headerState.width);

  useEffect(() => {
    const windowWidth = () => {
      dispatch(headerActions.modalWidth());
      if (width > 769) {
        dispatch(headerActions.closeModal());
      }
    };
    window.addEventListener("resize", windowWidth);
    return () => window.removeEventListener("resize", windowWidth);
  }, [width]);

  const modalEventHandler = () => {
    dispatch(headerActions.changeModal());
  };

  if (isOpen) {
    document.body.style.overflow = "hidden";
  } else {
    document.body.style.overflow = "auto";
  }

  return (
    <header className={styles.header}>
      {modal && <SideMenuModal user={props.user} />}
      <div className={styles.headerLeft}>
        <nav className={styles["header-nav"]}>
          <div className={styles["headerLeft-menu"]}>
            <i className="fa-solid fa-bars" onClick={modalEventHandler}></i>
          </div>
          <div className={styles["headerLeft-programIcon"]}>
            <img src={icon} alt="icon-userIcon" />
          </div>
          <ul className={styles["header-menu-container"]}>
            <li className={styles["header-menu-folder"]}>
              <a href="#!">Folder</a>
              <ul className={styles["header-menu-folderSub"]}>
                <li>
                  <a href="#!">Folder 1</a>
                </li>
                <li>
                  <a href="#!">Folder 2</a>
                </li>
                <li>
                  <a href="#!">Folder 3</a>
                </li>
              </ul>
            </li>
            <li>
              <a href="#!">Trash</a>
            </li>
          </ul>
        </nav>
      </div>
      <div className={styles["headerRight"]}>
        <div className={styles["header-night"]}>
          <i className="fa-solid fa-toggle-on"></i>
        </div>
      </div>
    </header>
  );
};

export default Header;
