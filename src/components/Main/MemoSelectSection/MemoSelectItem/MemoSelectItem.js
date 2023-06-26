import styles from "./MemoSelectItem.module.css";

const MemoSelectItem = (props) => {
  return (
    <span
      className={styles.memoSection}
      onClick={() => {
        props.changeCurrentSection(props.value);
      }}
    >
      {props.value}
      <hr className={props.currentSectionState ? styles.active : styles.hr} />
    </span>
  );
};

export default MemoSelectItem;
