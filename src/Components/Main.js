import React from "react";
import styles from "./Main.module.css";
import SavedMemo from "./SavedMemo/SavedMemo";
import WriteMemo from "./WriteMemo/WriteMemo";
import ImportMemoToggle from "./ImportMemo/ImportMemoToggle";

const Main = () => {
  return (
    <main>
      <WriteMemo />
      {/* <!-- 즐겨찾기 토글 --> */}
      <section>
        <ImportMemoToggle />
      </section>
      {/* <!-- 저장된 메모 영역 --> */}
      <div className={styles["main-savedMemo"]}>
        <ul className={styles["savedMemo-container"]}>
          {/* <!-- 예시 메모  --> */}
          <SavedMemo />
          <SavedMemo />
          <SavedMemo />
          <SavedMemo />
        </ul>
      </div>
    </main>
  );
};

export default Main;
