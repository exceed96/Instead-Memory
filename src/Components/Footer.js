import React from "react";
import styles from "./Footer.module.css";

const Footer = () => {
  const scrollTop = (evt) => {
    evt.preventDefault();
    if (window.scrollY > 0) {
      window.scrollTo({ top: 0, behavior: "smooth" });
    }
  };
  return (
    <footer className={styles.footer}>
      {/* <!-- 차후 부드러운 애니메이션 효과를 위해 JS로 변경 예정 --> */}
      <article className={styles.footerRight}>
        <a
          className={styles["footerRight-clickToTop"]}
          title="top"
          href="#!"
          onClick={scrollTop}
        >
          <i className="fa-solid fa-arrow-up"></i>
        </a>
      </article>
    </footer>
  );
};

export default Footer;
