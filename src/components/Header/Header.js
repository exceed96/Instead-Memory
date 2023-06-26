import styles from "./Header.module.css";

const Header = (props) => {
  return (
    <section className={styles.headerContainer}>
      <section className={styles.header}>
        <span className={styles.logo}>I'M</span>
        <section className={styles.profile}>
          <span className={styles.profileName}>{props.user}</span>
          <span className={styles.profileImage}></span>
        </section>
      </section>
    </section>
  );
};

export default Header;
