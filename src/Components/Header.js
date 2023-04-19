import React, { useState, useEffect } from "react";
import styles from "./Header.module.css";
import icon from "../assets/icon.png";
import SideMenuModal from "./Modal/SideMenuModal/SideMenuModal";

const Header = (props) => {
  const [modal, setModal] = useState(false);
  const [width, setWidth] = useState(window.innerWidth);
  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    const windowWidth = () => {
      setWidth(window.innerHeight);
      if (width > 769) {
        setModal(false);
      }
    };
    window.addEventListener("resize", windowWidth);
    return () => window.removeEventListener("resize", windowWidth);
  }, [width]);

  const modalEventHandler = () => {
    setModal((modal) => !modal);
    setIsOpen((isOpen) => !isOpen);
  };

  if (isOpen) {
    document.body.style.overflow = "hidden";
  } else {
    document.body.style.overflow = "auto";
  }

  return (
    <header className={styles.header}>
      {modal && (
        <SideMenuModal setModal={modalEventHandler} user={props.user} />
      )}
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
