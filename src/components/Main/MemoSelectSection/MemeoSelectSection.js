import styles from "./MemoSelectSection.module.css";
import MemoSelectItem from "./MemoSelectItem/MemoSelectItem";
import { useSelector, useDispatch } from "react-redux";
import { selectSectionActions } from "../../../store/selectSectionState";

const MemoSelectSection = (props) => {
  const dispatch = useDispatch();
  const currentSection = useSelector(
    (state) => state.selectSectionState.currentSection
  );
  const changeCurrentSection = (changeCurrent) => {
    if (changeCurrent === "NOTE") {
      dispatch(selectSectionActions.changeNoteSection());
    }
    if (changeCurrent === "IMPORTANT") {
      dispatch(selectSectionActions.changeImportantSection());
    }
    if (changeCurrent === "FOLDER") {
      dispatch(selectSectionActions.changeFolderSection());
    }
    if (changeCurrent === "DELETE") {
      dispatch(selectSectionActions.changeDeleteSection());
    }
  };
  return (
    <section className={styles.memoSelectContainer}>
      <MemoSelectItem
        changeCurrentSection={changeCurrentSection}
        value="NOTE"
        currentSectionState={currentSection.note}
      />
      <MemoSelectItem
        changeCurrentSection={changeCurrentSection}
        value="IMPORTANT"
        currentSectionState={currentSection.important}
      />
      <MemoSelectItem
        changeCurrentSection={changeCurrentSection}
        value="FOLDER"
        currentSectionState={currentSection.folder}
      />
      <MemoSelectItem
        changeCurrentSection={changeCurrentSection}
        value="DELETE"
        currentSectionState={currentSection.delete}
      />
    </section>
  );
};

export default MemoSelectSection;
