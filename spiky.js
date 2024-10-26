import http from 'k6/http';
import { check } from 'k6';

export const options = {
    scenarios: {
        spiky_load_test: {
            executor: 'ramping-arrival-rate',
            startRate: 50,
            timeUnit: '1s',
            stages: [
                { target: 200, duration: '30s' },
                { target: 100, duration: '10s' },
                { target: 300, duration: '30s' },
                { target: 0, duration: '10s' },
                { target: 200, duration: '20s' },
            ],
            preAllocatedVUs: 50,
            maxVUs: 100,
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
