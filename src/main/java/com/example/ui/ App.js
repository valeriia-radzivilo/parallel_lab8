import React, {useEffect, useState} from 'react';
import axios from 'axios';

function App() {
    const [matrices, setMatrices] = useState([]);

    useEffect(() => {
        const fetchMatrices = async () => {
            const response = await axios.get('/');
            setMatrices(response.data);
        };
        fetchMatrices();
    }, []);

    return (
        <div>
            {matrices.map((matrix, index) => (
                <div key={index}>
                    <h2>Matrix {index + 1}</h2>
                    {matrix.map((row, rowIndex) => (
                        <div key={rowIndex}>
                            {row.join(' ')}
                        </div>
                    ))}
                </div>
            ))}
        </div>
    );
}

export default App;