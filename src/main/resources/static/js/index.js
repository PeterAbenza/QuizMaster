let questionIndex = 0;

function addQuestion() {
    const container = document.getElementById('questions-container');

    const questionId = `question-${questionIndex}`;

    const questionHTML = `
        <div class="border p-3 mb-4 position-relative" id="${questionId}" style="background-color: #323548; border-color: black;">
            <button type="button" class="btn btn-danger btn-sm position-absolute" style="top: 10px; right: 10px;" onclick="removeQuestion('${questionId}')">
                <i class="fas fa-trash-alt"></i> Remover Pergunta
            </button>

            <div class="form-group" style="border-color: black;">
                <label>Pergunta</label>
                <input type="text" class="form-control" name="questions[${questionIndex}].text" required >
            </div>

            <label style="color: white;">Alternativas</label>
            <div class="form-group" style="background-color: #323548; border-color: black;">
                ${generateAlternativeWithRadio(questionIndex, 0)}
                ${generateAlternativeWithRadio(questionIndex, 1)}
                ${generateAlternativeWithRadio(questionIndex, 2)}
                ${generateAlternativeWithRadio(questionIndex, 3)}
            </div>
        </div>
    `;

    container.insertAdjacentHTML('beforeend', questionHTML);
    questionIndex++;
}

function generateAlternativeWithRadio(qIndex, altIndex) {
    return `
        <div class="mb-2">
            <input type="text" class="form-control mb-1" name="questions[${qIndex}].alternatives[${altIndex}].text" placeholder="Alternativa ${altIndex + 1}" required>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="questions[${qIndex}].correctIndex" value="${altIndex}" required>
                <label class="form-check-label">Correta</label>
            </div>
        </div>
    `;
}

function removeQuestion(id) {
    const questionElement = document.getElementById(id);
    if (questionElement) {
        questionElement.remove();
    }
}

