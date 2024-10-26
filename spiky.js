import http from 'k6/http';
import { check } from 'k6';

export const options = {
    scenarios: {
        spiky_load_test: {
            executor: 'ramping-arrival-rate',
            startRate: 50,           // start with 50 requests per second
            timeUnit: '1s',           // rate is defined per second
            stages: [
                { target: 200, duration: '30s' },  // ramp-up to 200 req/sec over 30 seconds
                { target: 100, duration: '10s' },  // drop to 100 req/sec for 10 seconds
                { target: 300, duration: '30s' },  // spike up to 300 req/sec over 30 seconds
                { target: 0, duration: '10s' },    // drop to zero for 10 seconds
                { target: 200, duration: '20s' },  // back to 200 req/sec for 20 seconds
            ],
            preAllocatedVUs: 50,      // initial virtual users
            maxVUs: 100,              // maximum virtual users
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
