
## Accessibility가 가장 높은 node 찾는 Dijkstra Algorithm

Accessibility: 거리가 D 이하인 node 개수

Total distance: 거리가 D 이하인 각 node까지의 거리의 합

Distance: 특정 node까지의 거리

## How to command

    {num of nodes} {num of edges}
    ...
    {start node}
    ...
    {Constraint of distance}

## Command Example

input 

    5 8
    1 3 6
    1 4 3
    2 1 3
    3 4 2
    4 2 1
    4 3 1
    5 2 4
    5 4 2
    5

output

    Best city 4
    Accessibility 3
    Total distance 6
    Path 4 2 1
    Distance 4
    Path 4 2
    Distance 1
    Path 4 3
    Distance 1
