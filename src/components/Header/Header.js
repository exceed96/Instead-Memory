import styles from "./Header.module.css";
import logo from "../../assets/logo.png";

const Header = (props) => {
  return (
    <section className={styles.headerContainer}>
      <section className={styles.header}>
        <img src={logo} className={styles.logo} alt="logo"></img>
        <section className={styles.profile}>
          <span className={styles.profileName}>{props.user}</span>
          <span className={styles.profileImage}></span>
        </section>
      </section>
    </section>
  );
};

export default Header;
