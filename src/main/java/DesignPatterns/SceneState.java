package DesignPatterns;

import Controllers.QuestionScene;

public interface SceneState {
    public void SceneConfig();
    public void OnNextQuestion();
    public void OnPrevQuestion();
    public void SetDifficulty(QuestionScene.DifficultyPolicy p);
}