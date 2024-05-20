import React, {useState} from 'react';
import axios from 'axios';

function App() {
    const [matrix1, setMatrix1] = useState([]);
    const [matrix2, setMatrix2] = useState([]);
    const [result, setResult] = useState([]);

    const multiply = async () => {
        const response = await axios.post('/multiply', {matrix1, matrix2});
        setResult(response.data);
    };

    return (
        <div>
            {/* Form for inputting matrix1 and matrix2 */}
            <button onClick={multiply}>Multiply</button>
            {/* Display the result */}
        </div>
    );
}

export default App;