import styles from "./MemoSelectItem.module.css";
import trashIcon from "../../../../assets/휴지통.png";

const MemoSelectItem = (props) => {
  const trashSelect = <img src={trashIcon} className={styles.trashIcon} alt="trash icon"></img>;
  return (
    <span
      className={
        props.currentSectionState
          ? styles.activeMemoSection
          : styles.memoSection
      }
      onClick={() => {
        props.changeCurrentSection(props.value);
      }}
    >
      {props.value === "DELETE" ? trashSelect : props.value}
      <section className={styles.lineContainer}>
        <section className={styles.lineLeft}>
          <hr
            className={props.currentSectionState ? styles.activeHr : styles.hr}
          />
        </section>
        <section className={styles.lineRight}>
          <hr
            className={props.currentSectionState ? styles.activeHr : styles.hr}
          />
        </section>
      </section>
    </span>
  );
};

export default MemoSelectItem;
