import http from 'k6/http';
import { check } from 'k6';

export const options = {
    scenarios: {
        constant_request_rate: {
            executor: 'constant-arrival-rate',
            rate: 1000,
            timeUnit: '1m',
            duration: '1m',
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
    

    check(res, {
        'is status 200': (r) => r.status === 200,
    });
}
