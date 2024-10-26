import http from 'k6/http';
import { check } from 'k6';

export const options = {
    scenarios: {
        constant_request_rate: {
            executor: 'constant-arrival-rate',
            rate: 1000, // 100 requests total
            timeUnit: '1m', // in 1 minute
            duration: '1m', // test duration
            preAllocatedVUs: 10, 
            maxVUs: 20, 
        },
    },
};

export default function () {
    const url = 'http://localhost:8000/';
    const payload = "TaskWriteOnFile&testing_write_on_the";
    
    const params = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
    };

    const res = http.post(url, payload, params);
    
    // Check if the response status is 200
    check(res, {
        'is status 200': (r) => r.status === 200,
    });
}
