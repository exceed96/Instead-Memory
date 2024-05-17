import logo from "../../assets/logo.png";
import errorIcon from "../../assets/errorIcon.png";
import styles from "./Expiration.module.css";
import { Link } from "react-router-dom";


const Expiration = () => {
  return (
    <section className={styles.errorContainer}>
      <header className={styles.errorHeader}>
        <img src={logo} alt="logo"></img>
      </header>
      <img src={errorIcon} className={styles.errorIcon} alt="error icon"></img>
      <main className={styles.errorMainContainer}>
        <section className={styles.errorContent}>
          <strong>문제가 발생했습니다.</strong>
          <strong>문제가 발생하여 페이지를 읽어들이지 못했습니다</strong>
        </section>
        <button className={styles.loginRedirectButton}>
          <Link to="/" className={styles.loginRedirectLink}>
            로그인 다시 시도
          </Link>
        </button>
      </main>
    </section>
  );
};

export default Expiration;
