import React, {useEffect, useState} from 'react';
import axios from 'axios';

function App() {
    const [matrices, setMatrices] = useState([]);
    const [generatedMatrix, setGeneratedMatrix] = useState([]);
    const [userMatrix, setUserMatrix] = useState([]);

    useEffect(() => {
        const fetchMatrices = async () => {
            const response = await axios.get('/');
            setMatrices(response.data);
        };
        fetchMatrices();
    }, []);

    const generateMatrix = () => {
        const newMatrix = Array.from({length: 3}, () => Array.from({length: 3}, () => Math.floor(Math.random() * 10)));
        setGeneratedMatrix(newMatrix);
    };

    const handleUserInput = () => {
        const userInput = prompt("Please enter a 3x3 matrix (9 numbers separated by commas):");
        if (userInput) {
            const newMatrix = userInput.split(',').map(Number);
            if (newMatrix.length === 9) {
                setUserMatrix([
                    newMatrix.slice(0, 3),
                    newMatrix.slice(3, 6),
                    newMatrix.slice(6, 9)
                ]);
            } else {
                alert("Invalid input. Please enter 9 numbers separated by commas.");
            }
        }
    };

    const multiplyMatrices = async (firstMatrix, secondMatrix, threadCount) => {
        const response = await axios.post('/multiply', {
            firstMatrix,
            secondMatrix,
            threadCount
        });
        return response.data;
    };

    const handleMultiplyClick = async () => {
        const result = await multiplyMatrices(generatedMatrix, userMatrix, 4);
        console.log(result);
    };

    return (
        <div>
            <button onClick={generateMatrix}>Generate Matrix</button>
            <button onClick={handleUserInput}>Input Matrix</button>
            <button onClick={handleMultiplyClick}>Multiply Matrices</button>
            {matrices.map((matrix, index) => (
                <div key={index}>
                    <h2>Matrix {index + 1}</h2>
                    <table>
                        <tbody>
                        {matrix.map((row, rowIndex) => (
                            <tr key={rowIndex}>
                                {row.map((item, itemIndex) => (
                                    <td key={itemIndex}>{item}</td>
                                ))}
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            ))}
        </div>
    );
}

export default App;