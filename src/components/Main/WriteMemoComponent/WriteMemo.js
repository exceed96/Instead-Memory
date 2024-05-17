import WriteMemoButton from "./WriteMemoButton/WriteMemoButton";
import styles from "./WriteMemo.module.css";

const WriteMemo = (props) => {
  return (
    <>
      <section className={styles.writeMemoSection}>
        <WriteMemoButton title="NOTE" count={props.entireMemoCount} />
        <WriteMemoButton
          title="IMPORTANT"
          count={props.importMemoCount}
          important="true"
        />
      </section>
    </>
  );
};

export default WriteMemo;
