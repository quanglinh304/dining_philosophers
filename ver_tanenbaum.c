#include <stdio.h>
#include <windows.h>
#include <semaphore.h>
#include <stdlib.h>
#include <pthread.h>
// #include <thread>

#define N 5
#define THINKING 0
#define HUNGRY 1
#define EATING 2

sem_t sem[N];
int philosopher[N];
int state[N];
pthread_mutex_t mutex;


int leftPhil(int i){
	return (i + 4) % N;
}	
int rightPhil(int i){
	return (i + 1) % N;
}
	

void test(int i){
	if (state[i] == HUNGRY && 
		state[leftPhil(i)] != EATING && 
		state[rightPhil(i)] != EATING){
		state[i] = EATING;

		Sleep(rand() % 1000 + 1000);

		printf("Philosopher %d is taking the fork %d and fork %d\n", (i+1), (i+1), (rightPhil(i) + 1));
		printf("Philosopher %d is eating...\n", (i+1));

		sem_post(&sem[i]);
	}
}
void takeFork(int i){
	pthread_mutex_lock(&mutex);
	state[i] = HUNGRY;

	printf("Philosopher %d is hungry...\n", (i+1));
	
	Sleep(rand() % 1000 + 1000); 

	test(i);

	pthread_mutex_unlock(&mutex);

	sem_wait(&sem[i]);
}
void putFork(int i){
	pthread_mutex_lock(&mutex);

	state[i] = THINKING;
	printf("Philosopher %d is putting down the fork %d and fork %d\n", (i+1), (i+1),
																 (rightPhil(i) + 1));

	test(rightPhil(i));
	test(leftPhil(i));

	pthread_mutex_unlock(&mutex);
}

void* run(void *num){
	while(1){
		int *i = num;
		Sleep(rand() % 1000 + 1000);

		takeFork(*i);

		Sleep(rand() % 1000 + 1000);

		putFork(*i);
	}
}

int main(){
	for (int i = 0; i < 5; i++){
		sem_init(&sem[i], 0, 0);
		philosopher[i] = i;
		state[i] = THINKING;
	}

	pthread_t threads[N];

	for (int i = 0; i < N; i++){
		pthread_create(&threads[i], NULL, run, &philosopher[i]);
	}
	for (int i = 0; i < N; i++){
		pthread_join(threads[i], NULL);
	}
	return 0;
}
